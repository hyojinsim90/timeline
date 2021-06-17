import React, { useState, useEffect } from "react"
import styled from "styled-components"
import { Form, Input, Select, Button, Divider, Tag } from "antd"
import { useHistory } from "react-router-dom"
import Axios from "axios"
import { PlusCircleOutlined, CloseOutlined } from "@ant-design/icons"
import TimelineDetail from "../../CreateTimeline/Sections/TimelineDetail"
import { useSelector } from "react-redux"
import TimelineView from "../../CreateTimeline/Sections/TimelineView"
import UploadImage from "../../CreateTimeline/Sections/UploadImage"

const ModifyTimelineDiv = styled.div`
  padding: 3rem 0;
  form {
    width: 100%;
    padding: 3rem;
    display: inline-block;
    .ant-form-item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      .ant-form-item-label {
        text-align: center;
      }
    }
    .ant-form-item:last-child {
      .ant-form-item-control-input-content {
        div:nth-child(2) {
          margin-top: 20px;
          align-items: center;
          display: flex;
          justify-content: center;
        }
      }
    }
    label {
      margin-bottom: 1rem;
    }
    a button {
      width: 100%;
      background: black;
      color: #ffffff;
      margin-top: 100px;
    }
  }
`

const ModifyDetailDiv = styled.div`
  display: flex;
  width: 100%;
  div {
    width: 100%;
  }
`

const { Option } = Select

const ModifyTimeline = (props) => {
  const [title, setTitle] = useState("")
  const [countList, setCountList] = useState([0])
  const [detailTitle, setDetailTitle] = useState([])
  const [detailDate, setDetailDate] = useState([])
  const [detailDateString, setDetailDateString] = useState([])
  const [detailContent, setDetailContent] = useState([])
  const [category, setCategory] = useState("")
  const [complete, setComplete] = useState(false)
  const [open, setOpen] = useState(false)
  const [files, setFiles] = useState([])
  const [filepath, setFilepath] = useState("")
  const [deleteStatus, setDeleteStatus] = useState(false)

  const user = useSelector(state => state.user)
  const history = useHistory()

  useEffect(() => {
    const countArr = [...countList]

    if(props.timeline[0] !== undefined && props.detail !== undefined) {
      setTitle(props.timeline[0].title)
      setCategory(props.timeline[0].category)
      setComplete(props.timeline[0].complete)
      setOpen(props.timeline[0].open)

      if(props.timeline[0].filePath !== null) {
        const path = props.timeline[0].filePath.split("-")[1]
        setFilepath(path)
      }

      props.detail.forEach((item, i) => {
        const date = item.scheduleDate.slice(0, 4) + "-" + item.scheduleDate.slice(4, 6) + "-" + item.scheduleDate.slice(6, 8)
        countArr[i] = i
        detailTitle[i] = item.title
        detailDateString[i] = date
        detailContent[i] = item.content
      })

      setCountList(countArr)
      setDetailTitle(detailTitle)
      setDetailDate(detailDateString)
      setDetailContent(detailContent)
    }
  }, [props.timeline, props.detail])

  const onModifyTimeline = (e) => {

    let formData = new FormData()
    let variables = [{
      category: category,
      complete: complete,
      open: open,
      title: title,
    }]
    console.log(deleteStatus);
    // 파일 업로드 안 했을 때 기본 이미지 혹은 기존 이미지 그대로 유지
    if(files.length === 0) {
      formData.append("file", "")
      // 기존에 업로드한 파일 삭제하고 재업로드 안 했을 경우 filepath ""로 보냄
      if(deleteStatus) {
        variables = [{
          category: category,
          complete: complete,
          open: open,
          title: title,
          filePath: "",
          imgFullPath: ""
        }]
      // 기존에 업로드한 파일 삭제한 적 없고 재업로드 안 했을 경우에도 기존/기본 파일 유지
      } else {
        variables = [{
          category: category,
          complete: complete,
          open: open,
          title: title,
          filePath: props.timeline[0].filePath,
          imgFullPath: props.timeline[0].imgFullPath
        }]
      }
    // 파일 업로드했을 때 새로운 이미지로 교체
    } else {
      formData.append("file", files[0])
    }

    formData.append("dto", new Blob([JSON.stringify(variables)], {type: "application/json"}))

    let detailList = []
    let valid = true

    // 타임라인이 하나도 없을 때
    if(countList.length === 0) {
      alert("1개 이상의 타임라인을 작성해 주세요")
      return false
    } else if(!title) {
      alert("제목을 작성해 주세요")
      return false
    }

    // 타임라인 상세 내용 유효성 검사
    countList.forEach((item, i) => {
      if(detailTitle[i] === undefined || detailTitle[i] === "") {
        alert("타임라인 제목을 입력해 주세요")
        valid = false
      } else if(detailContent[i] === undefined || detailContent[i] === "") {
        alert("타임라인 내용을 입력해 주세요")
        valid = false
      } else if(detailDate[i] === undefined || detailDate[i] === "") {
        alert("타임라인 날짜를 입력해 주세요")
        valid = false
      }
    })

    if(valid) {
      // timeline master 수정
      Axios.put(`/timeline/master/${props.timelineId}`, formData)
        .then(res => {
          if(res.data) {
            // timeline detail 전체 삭제 후 새로 save
            Axios.delete(`/timeline/detail/${props.timelineId}`)
             .then(response => {
               if(response.status === 200) {
                 countList.forEach((item, i) => {
                   detailList.push({
                     "content": detailContent[i],
                     "id": res.data.id.toString() + i.toString(),
                     "masterId": res.data.id,
                     "scheduleDate": detailDateString[i].replaceAll('-', ''),
                     "title": detailTitle[i]
                   })
                 })

                 Axios.post("/timeline/detail/save", detailList)
                   .then(response => {
                     if(response.status === 200) {
                       props.history.push("/mytimeline")
                     }
                   })
               }
             })
          }
        })
    }
  }

  const onChangeTitle = (e) => {
    setTitle(e.target.value)
  }

  const onChangeDetailTitle= (e, i) => {
    let detailTitleArr = [...detailTitle]

    detailTitleArr[i] = e.target.value
    setDetailTitle(detailTitleArr)
  }

  const onChangeDate = (date, dateString, i) => {
    let detailDateArr = [...detailDate]
    let detailDatestringArr = [...detailDateString]

    // 날짜 선택 중복 금지
    if(detailDatestringArr.includes(dateString)) {
      alert("이미 선택한 날짜입니다")
      return false
    } else {
      detailDateArr[i] = date
      detailDatestringArr[i] = dateString
      setDetailDate(detailDateArr)
      setDetailDateString(detailDatestringArr)
    }
  }

  const onchangeDetailContent = (e, i) => {
    let detailContentArr = [...detailContent]

    detailContentArr[i] = e.target.value
    setDetailContent(detailContentArr)
  }

  const onSelectCategory = (value) => {
    setCategory(value)
  }

  const onSelectComplete = (value) => {
    setComplete(value)
  }

  const onSelectOpen = (value) => {
    setOpen(value)
  }

  const onAddDetailDiv = () => {
    let countArr = countList.length === 0 ? [0] : [...countList]
    let counter = countArr.slice(-1)[0]

    // 타임라인 상세 영역 다 삭제해서 하나도 없을 때
    if(countList.length === 0) {
      setCountList(countArr)
    // 타임라인 상세 영역 1개 이상일 때
    } else {
      counter += 1
      countArr[countArr.length] = counter
      setCountList(countArr)
    }
  }

  const onDeleteDetailTitle = (i) => {
    let titleArr = [...detailTitle]
    titleArr.splice(i, 1)
    setDetailTitle(titleArr)
  }

  const onDeleteDetailDate = (i) => {
    let dateArr = [...detailDate]
    let datestringArr = [...detailDateString]

    dateArr.splice(i, 1)
    datestringArr.splice(i, 1)
    setDetailDate(dateArr)
    setDetailDateString(datestringArr)
  }

  const onDeleteDetailContent = (i) => {
    let contentArr = [...detailContent]
    contentArr.splice(i, 1)
    setDetailContent(contentArr)
  }

  const onDeleteDetail = (item, i) => {
    let countArr = [...countList]
    // array 값과 일치하는 것만 삭제, 일치하지 않는 것들만 남김
    const newCountArr = countArr.filter((arr) => arr !== item)
    setCountList(newCountArr)
    onDeleteDetailTitle(i)
    onDeleteDetailDate(i)
    onDeleteDetailContent(i)
  }

  const onDrop = (files) => {
    setFiles(files)
  }

  const onDeleteFile = () => {
    Axios.delete(`/timeline/master/${props.timeline[0].filePath}`)
      .then(res => {
        if(res.status === 200) {
          setFilepath("")
          setDeleteStatus(true)
        }
      })
  }

  return (
    <ModifyTimelineDiv>
      <h1>타임라인 수정하기</h1>
      <br />
      <Form onSubmit={onModifyTimeline}>
        <div>
          <Form.Item
            label="타임라인 제목"
          >
            <Input
              type="text"
              onChange={onChangeTitle}
              value={title}
            />
          </Form.Item>
          <Form.Item
            label="분야"
          >
            <Select value={category} onChange={onSelectCategory}>
              <Option value="생활">생활</Option>
              <Option value="여행">여행</Option>
              <Option value="문화">문화</Option>
              <Option value="경제">경제</Option>
              <Option value="기타">기타</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="진행 여부"
          >
            <Select value={complete.toString()} onChange={onSelectComplete}>
              <Option value="false">진행중</Option>
              <Option value="true">진행완료</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="공개 여부"
          >
            <Select value={open.toString()} onChange={onSelectOpen}>
              <Option value="false">비공개</Option>
              <Option value="true">공개</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="이미지"
          >
            <UploadImage onDrop={onDrop} />
            {files[0] ?
              <div>
                <Tag color="black">{files[0].path}</Tag>
                <CloseOutlined onClick={onDeleteFile} />
              </div>
              :
              filepath &&
              <div>
                <Tag color="black">{filepath}</Tag>
                <CloseOutlined onClick={onDeleteFile} />
              </div>
            }
          </Form.Item>
        </div>
        <Divider />
        <ModifyDetailDiv>
          <div>
            <TimelineDetail countList={countList} onDeleteDetail={onDeleteDetail} onChangeDetailTitle={onChangeDetailTitle} detailTitle={detailTitle}
              onChangeDate={onChangeDate} detailDate={detailDate} onchangeDetailContent={onchangeDetailContent} detailContent={detailContent} detailDateString={detailDateString} />
            <Button onClick={onAddDetailDiv}>
              <PlusCircleOutlined />추가
            </Button>
          </div>
          <div>
            <TimelineView countList={countList} detailTitle={detailTitle} detailDateString={detailDateString} detailContent={detailContent}/>
          </div>
        </ModifyDetailDiv>
        <Button size="large" onClick={onModifyTimeline}>수정하기</Button>
      </Form>
    </ModifyTimelineDiv>
  )
}
export default ModifyTimeline

import React, { useState } from "react"
import styled from "styled-components"
import { Form, Input, Select, Button, Divider, Tag } from "antd"
import { useHistory } from "react-router-dom"
import Axios from "axios"
import { PlusCircleOutlined } from "@ant-design/icons"
import TimelineDetail from "./Sections/TimelineDetail"
import { useSelector } from "react-redux"
import TimelineView from "./Sections/TimelineView"
import UploadImage from "./Sections/UploadImage"

const CreateTimelineDiv = styled.div`
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
      .ant-tag {
        margin-top: 20px;
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

const CreateDetailDiv = styled.div`
  display: flex;
  width: 100%;
  div {
    width: 100%;
  }
`

const { Option } = Select

const CreateTimeline = () => {
  const [title, setTitle] = useState("")
  const [countList, setCountList] = useState([0])
  const [detailTitle, setDetailTitle] = useState([])
  const [detailDate, setDetailDate] = useState([])
  const [detailDateString, setDetailDateString] = useState([])
  const [detailContent, setDetailContent] = useState([])
  const [category, setCategory] = useState("경제")
  const [complete, setComplete] = useState(false)
  const [open, setOpen] = useState(false)
  const [files, setFiles] = useState([])

  const user = useSelector(state => state.user)
  const history = useHistory()

  const onCreateTimeline = (e) => {

    let formData = new FormData()

    formData.append("file", files[0])

    let variables = [{
      author: user.userData.email,
      category: category,
      complete: complete,
      open: open,
      title: title,
      likeCount: 0,
      viewCount: 0
    }]

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
      Axios.post("/timeline/master/save", formData)
        .then(res => {
          // master에서 id값 return하면 받아서 detail 저장
          if(res.data.id) {
            countList.forEach((item, i) => {
              let year = detailDate[i]._d.getFullYear().toString()
              let month = (detailDate[i]._d.getMonth() + 1) < 10 ? "0" + (detailDate[i]._d.getMonth() + 1) : (detailDate[i]._d.getMonth() + 1).toString()
              let date = (detailDate[i]._d.getDate()) < 10 ? "0" + (detailDate[i]._d.getDate()) : detailDate[i]._d.getDate()

              detailList.push({
                "content": detailContent[i],
                "id": res.data.id.toString() + i.toString(),
                "masterId": res.data.id,
                "scheduleDate": year + month + date,
                "title": detailTitle[i]
              })
            })

            Axios.post("/timeline/detail/save", detailList)
              .then(response => {
                if(response.status === 200) {
                  history.push("/mytimeline")
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

    detailDateArr[i] = date
    detailDatestringArr[i] = dateString
    setDetailDate(detailDateArr)
    setDetailDateString(detailDatestringArr)
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
    // 파일 크기 200MB 이하로 제한
    if(files[0].size > 200000000) {
      alert("파일 크기가 너무 큽니다 200MB 이하 파일만 업로드 가능합니다")
    } else {
      setFiles(files)
    }
  }

  return (
    <CreateTimelineDiv>
      <h1>타임라인 생성하기</h1>
      <br />
      <Form onSubmit={onCreateTimeline}>
        <div>
          <Form.Item
            label="타임라인 제목"
            name="title"
          >
            <Input
              type="text"
              onChange={onChangeTitle}
              value={title}
              required
            />
          </Form.Item>
          <Form.Item
            label="분야"
          >
            <Select defaultValue="생활" onChange={onSelectCategory}>
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
            <Select defaultValue="false" onChange={onSelectComplete}>
              <Option value="false">진행중</Option>
              <Option value="true">진행완료</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="공개 여부"
          >
            <Select defaultValue="false" onChange={onSelectOpen}>
              <Option value="false">비공개</Option>
              <Option value="true">공개</Option>
            </Select>
          </Form.Item>
          <Form.Item
            label="이미지"
          >
            <UploadImage onDrop={onDrop} />
            {files[0] &&
              <Tag color="black">{files[0].path}</Tag>
            }
          </Form.Item>
        </div>
        <Divider />
        <CreateDetailDiv>
          <div>
            <TimelineDetail countList={countList} onDeleteDetail={onDeleteDetail} onChangeDetailTitle={onChangeDetailTitle} detailTitle={detailTitle}
              onChangeDate={onChangeDate} detailDate={detailDate} onchangeDetailContent={onchangeDetailContent} detailContent={detailContent} />
            <Button onClick={onAddDetailDiv}>
              <PlusCircleOutlined />추가
            </Button>
          </div>
          <div>
            <TimelineView countList={countList} detailTitle={detailTitle} detailDateString={detailDateString} detailContent={detailContent}/>
          </div>
        </CreateDetailDiv>
        <Button size="large" onClick={onCreateTimeline}>생성하기</Button>
      </Form>
    </CreateTimelineDiv>
  )
}
export default CreateTimeline

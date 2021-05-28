import React, { useState } from "react"
import styled from "styled-components"
import { Form, Input, Select, Button, DatePicker, Divider } from "antd"
import { Link } from "react-router-dom"
import Axios from "axios"
import { PlusCircleOutlined } from "@ant-design/icons"
import TimelineDetail from "./Sections/TimelineDetail"
import { useSelector } from "react-redux"

const CreateTimelineDiv = styled.div`
  padding: 3rem 0;
  form {
    width: 320px;
    display: inline-block;
    .ant-form-item {
      display: flex;
      flex-direction: column;
      justify-content: center;
      .ant-form-item-label {
        text-align: center;
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

const { Option } = Select

const CreateTimeline = () => {
  const [title, setTitle] = useState("")
  const [countList, setCountList] = useState([0])
  const [detailTitle, setDetailTitle] = useState([])
  const [detailDate, setDetailDate] = useState([])
  const [detailContent, setDetailContent] = useState([])
  const [category, setCategory] = useState("경제")
  const [complete, setComplete] = useState(false)
  const [open, setOpen] = useState(false)

  const user = useSelector(state => state.user)

  const onCreateTimeline = (e) => {
    let variables = {
      "author": user.userData.email,
      "category": category,
      "complete": complete,
      "open": open,
      "title": title
    }

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
      Axios.post("/timeline/master/save", variables)
        .then(res => {
          // master에서 id값 return하면 받아서 detail 저장
          if(res.data.id) {
            countList.forEach((item, i) => {
              let year = detailDate[i]._d.getFullYear().toString()
              let month = (detailDate[i]._d.getMonth() + 1) < 10 ? "0" + (detailDate[i]._d.getMonth() + 1) : (detailDate[i]._d.getMonth() + 1).toString()
              let date = (detailDate[i]._d.getDate()) < 10 ? "0" + (detailDate[i]._d.getDate()) : detailDate[i]._d.getDate()

              detailList.push({
                "content": detailContent[i],
                "masterId": res.data.id,
                "scheduleDate": year + month + date,
                "title": detailTitle[i]
              })
            })

            Axios.post("/timeline/detail/save", detailList)
              .then(response => {
                console.log(response);
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

    detailDateArr[i] = date
    setDetailDate(detailDateArr)
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
    dateArr.splice(i, 1)
    setDetailDate(dateArr)
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

  return (
    <CreateTimelineDiv>
      <h1>타임라인 생성하기</h1>
      <br />
      <Form onSubmit={onCreateTimeline}>
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
          <Select defaultValue="경제" onChange={onSelectCategory}>
            <Option value="경제">경제</Option>
            <Option value="사회">사회</Option>
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
        <TimelineDetail countList={countList} onDeleteDetail={onDeleteDetail} onChangeDetailTitle={onChangeDetailTitle} detailTitle={detailTitle}
          onChangeDate={onChangeDate} detailDate={detailDate} onchangeDetailContent={onchangeDetailContent} detailContent={detailContent} />
        <Button onClick={onAddDetailDiv}>
          <PlusCircleOutlined />추가
        </Button>
        <Divider />

          <Button size="large" onClick={onCreateTimeline}>생성하기</Button>

      </Form>
    </CreateTimelineDiv>
  )
}
export default CreateTimeline
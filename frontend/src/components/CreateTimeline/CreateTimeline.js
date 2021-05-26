import React, { useState } from "react"
import styled from "styled-components"
import { Form, Input, Select, Button, DatePicker, Divider } from "antd"
import { Link } from "react-router-dom"
import Axios from "axios"
import { PlusCircleOutlined } from "@ant-design/icons"
import TimelineDetail from "./Sections/TimelineDetail"

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

  const onCreateTimeline = () => {
    // Axios.post("/timeline/master/save")
    //   .then()
  }

  const onChangeTitle = (e) => {
    setTitle(e.target.value)
  }

  const onChangeDetailTitle= (e, i) => {
    let detailTitleArr = [...detailTitle]

    detailTitleArr[i] = e.target.value
    setDetailTitle(detailTitleArr)
  }

  const onChangeDate = (date, dateString) => {
    console.log(date, dateString);
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

  const onDeleteDetail = (item, i) => {
    let countArr = [...countList]
    // array 값과 일치하는 것만 삭제, 일치하지 않는 것들만 남김
    const newCountArr = countArr.filter((arr) => arr !== item)
    setCountList(newCountArr)
    onDeleteDetailTitle(i)
  }

  return (
    <CreateTimelineDiv>
      <h1>타임라인 생성하기</h1>
      <br />
      <Form>
        <Form.Item
          label="타임라인 제목"
          name="title"
        >
          <Input
            type="text"
            onChange={onChangeTitle}
            required
          />
        </Form.Item>
        <Form.Item
          label="분야"
        >
          <Select defaultValue="경제">
            <Option value="economic">경제</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="진행 여부"
        >
          <Select defaultValue="ing">
            <Option value="ing">진행중</Option>
            <Option value="complete">진행완료</Option>
          </Select>
        </Form.Item>
        <Form.Item
          label="공개 여부"
        >
          <Select defaultValue="private">
            <Option value="private">비공개</Option>
            <Option value="public">공개</Option>
          </Select>
        </Form.Item>
        <TimelineDetail countList={countList} onDeleteDetail={onDeleteDetail} onChangeDetailTitle={onChangeDetailTitle} detailTitle={detailTitle} />
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

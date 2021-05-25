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

  const onCreateTimeline = () => {
    // Axios.post("/timeline/master/save")
    //   .then()
  }

  const onChangeTitle = (e) => {
    setTitle(e.target.value)
  }

  const onChangeDate = (date, dateString) => {
    console.log(date, dateString);
  }

  const onAddDetailDiv = () => {
    let countArr = [...countList]
    let counter = countArr.slice(-1)[0]
    counter += 1
    countArr[counter] = counter
    setCountList(countArr)
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
        <TimelineDetail countList={countList} onAddDetailDiv={onAddDetailDiv} />
        <Button onClick={onAddDetailDiv}>
          <PlusCircleOutlined />추가
        </Button>
        <Divider />
        <Link to="/timeline">
          <Button size="large" onClick={onCreateTimeline}>생성하기</Button>
        </Link>
      </Form>
    </CreateTimelineDiv>
  )
}
export default CreateTimeline

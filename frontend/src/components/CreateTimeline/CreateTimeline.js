import React, { useState } from "react"
import styled from "styled-components"
import { Form, Input, Select, Button, DatePicker, Divider } from "antd"
import { Link } from "react-router-dom"
import Axios from "axios"
import { PlusCircleOutlined } from "@ant-design/icons"

const { TextArea } = Input

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
    .ant-divider + div {
      border: 1px solid lightgray;
    }
    a button {
      width: 100%;
      background: black;
      color: #ffffff;
      margin-top: 1rem;
    }
  }
`

const { Option } = Select

const CreateTimeline = () => {
  const [countDiv, setCountDiv] = useState(0)

  const onCreateTimeline = () => {
    Axios.post("/timeline/master/save")
      .then()
  }

  const onChangeDate = (date, dateString) => {
    console.log(date, dateString);
  }

  const onAddDetailDiv = () => {
    let count = countDiv
    setCountDiv(count+1)
  }

  const renderItems = () => {
    // for(var i=0; i<2; i++) {
    //   <div>
    //     d
    //   </div>
    // }
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
        <Divider />
        <div>
          <Form.Item
            label="타임라인 상세 제목"
          >
            <Input
              type="text"
              required
            />
          </Form.Item>
          <Form.Item
            label="내용"
          >
            <div>
              <DatePicker onChange={onChangeDate} />
              <TextArea
                autoSize={{ minRows: 6, maxRows: 6 }}
              />
            </div>
          </Form.Item>
        </div>
        <Button onClick={onAddDetailDiv}>
          <PlusCircleOutlined />추가
        </Button>
        <Link to="/timeline">
          <Button size="large" onClick={onCreateTimeline}>생성하기</Button>
        </Link>
      </Form>
    </CreateTimelineDiv>
  )
}
export default CreateTimeline

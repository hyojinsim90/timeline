import React from "react"
import { Form, Input, Select, Button, Divider, Tag } from "antd"
import UploadImage from "./UploadImage"
import { CloseOutlined } from "@ant-design/icons"

const { Option } = Select

const TimelineMaster = (props) => {
  return (
    <div>
      <Form.Item
        label="타임라인 제목"
        name="title"
      >
        <Input
          type="text"
          onChange={props.onChangeTitle}
          value={props.title}
          required
        />
      </Form.Item>
      <Form.Item
        label="분야"
      >
        <Select defaultValue="생활" onChange={props.onSelectCategory}>
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
        <Select defaultValue="false" onChange={props.onSelectComplete}>
          <Option value="false">진행중</Option>
          <Option value="true">진행완료</Option>
        </Select>
      </Form.Item>
      <Form.Item
        label="공개 여부"
      >
        <Select defaultValue="false" onChange={props.onSelectOpen}>
          <Option value="false">비공개</Option>
          <Option value="true">공개</Option>
        </Select>
      </Form.Item>
      <Form.Item
        label="이미지"
      >
        <UploadImage onDrop={props.onDrop} />
        {props.files[0] &&
          <div>
            <Tag color="black">{props.files[0].path}</Tag>
            <CloseOutlined onClick={props.onDeleteFile} />
          </div>
        }
      </Form.Item>
    </div>
  )
}

export default TimelineMaster

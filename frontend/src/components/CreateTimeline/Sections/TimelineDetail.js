import React from "react"
import { Form, Input, DatePicker, Divider } from "antd"
import styled from "styled-components"

const DetailDiv = styled.div`
  div {
    display: flex;
    flex-direction: column;
    div {
      margin-bottom: 20px;
      .ant-picker-input {
        display: flex;
        flex-direction: row;
      }
    }
  }
`

const { TextArea } = Input

const TimelineDetail = (props) => {

  return (
    <DetailDiv>
      {props.countList && props.countList.map((item, i) => (
        <div key={i}>
          <Divider />
          <div>
            <label>타임라인 상세 제목</label>
            <Input
              type="text"
              required
            />
          </div>
          <div>
            <label>날짜</label>
            <DatePicker />
          </div>
          <div>
            <label>내용</label>
            <TextArea
              autoSize={{ minRows: 6, maxRows: 6 }}
            />
          </div>
        </div>
      ))}
    </DetailDiv>
  )
}

export default TimelineDetail

import React from "react"
import { Form, Input, DatePicker, Divider, Button } from "antd"
import styled from "styled-components"
import { CloseSquareOutlined } from "@ant-design/icons"

const DetailDiv = styled.div`
  div:first-child .ant-divider {
    border: none;
  }
  div {
    display: flex;
    flex-direction: column;
    div {
      button {
        display: flex;
        justify-content: flex-end;
        border: none;
        outline: none;
        color: #FF4B2B;
        &:active: {
          border: none;
          outline: none;
        }
        &::after {
          border: none;
          outline: none;

        }
      }
      margin-bottom: 20px;
      .ant-picker {
        height: 32px;
        .ant-picker-input {
          display: flex;
          flex-direction: row;
        }
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
            {item}
            <Button onClick={() => props.onDeleteDetail(item, i)}>
              <CloseSquareOutlined />
            </Button>
            <label>타임라인 상세 제목</label>
            <Input
              type="text"
              value={props.detailTitle[i]}
              onChange={(e) => props.onChangeDetailTitle(e, i)}
              required
            />
          </div>
          <div>
            <label>날짜</label>
            <DatePicker
              value={props.detailDate[i]}
              onChange={(date, dateString) => props.onChangeDate(date, dateString, i)}
            />
          </div>
          <div>
            <label>내용</label>
            <TextArea
              autoSize={{ minRows: 6, maxRows: 6 }}
              value={props.detailContent[i]}
              onChange={(e) => props.onchangeDetailContent(e, i)}
            />
          </div>
        </div>
      ))}
    </DetailDiv>
  )
}

export default TimelineDetail

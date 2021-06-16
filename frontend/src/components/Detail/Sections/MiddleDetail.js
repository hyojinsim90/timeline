import React from "react"
import { Timeline, TimelineItem }  from "vertical-timeline-component-for-react"
import styled from "styled-components"
import { Input } from "antd"

const MiddleDetailDiv = styled.div`
  width: 90%;
  .entry {
    color: #e86971;
    height: 495px;
  }
  .body-container p {
    word-break: break-all;
    .ant-input {
      background: #efefef;
    }
  }
`

const { TextArea } = Input

const MiddleDetail = (props) => {
  return (
    <MiddleDetailDiv>
      <Timeline lineColor={'black'}>
        {props.countList && props.countList.map((item, i) => (
          <TimelineItem
            key={i}
            id={props.id[i]}
            dateText={props.detailDateString !== undefined ? props.detailDateString[i] : ""}
            bodyContainerStyle={{
              background: '#efefef',
              padding: '20px',
              borderRadius: '8px',
              marginBottom: "100px",
            }}
          >
            <h3>{props.title[i]}</h3>
            <TextArea
              autoSize={true}
              bordered={false}
              value={props.content[i]}
            />
          </TimelineItem>
        ))}
      </Timeline>
    </MiddleDetailDiv>
  )
}

export default MiddleDetail

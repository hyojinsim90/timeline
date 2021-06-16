import React from "react"
import { Timeline, TimelineItem }  from "vertical-timeline-component-for-react"
import styled from "styled-components"
import { Input } from "antd"

const TimelineViewDiv = styled.div`
  .body-container p {
    word-break: break-all;
    .ant-input {
      background: #efefef;
    }
  }
`

const { TextArea } = Input

const TimelineView = (props) => {
  return (
    <TimelineViewDiv>
      <Timeline lineColor={'black'}>
        {props.countList && props.countList.map((item, i) => (
          <TimelineItem
            key={i}
            dateText={props.detailDateString[i]}
            style={{ color: '#e86971', height: '495px', }}
            bodyContainerStyle={{
              background: '#efefef',
              padding: '20px',
              borderRadius: '8px',
            }}
          >
            <h3>{props.detailTitle[i]}</h3>
            <TextArea
              autoSize={true}
              bordered={false}
              value={props.detailContent[i]}
            />
          </TimelineItem>
        ))}
      </Timeline>
    </TimelineViewDiv>
  )
}

export default TimelineView

import React from "react"
import { Timeline, TimelineItem }  from 'vertical-timeline-component-for-react'
import styled from "styled-components"

const TimelineViewDiv = styled.div`
  .body-container p {
    word-break: break-all;
  }
`

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
              background: '#ddd',
              padding: '20px',
              borderRadius: '8px',
              boxShadow: '0.5rem 0.5rem 2rem 0 rgba(0, 0, 0, 0.2)',
            }}
          >
            <h3>{props.detailTitle[i]}</h3>
            <p>
              {props.detailContent[i]}
            </p>
          </TimelineItem>
        ))}
      </Timeline>
    </TimelineViewDiv>
  )
}

export default TimelineView

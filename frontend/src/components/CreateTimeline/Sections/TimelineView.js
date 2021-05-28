import React from "react"
import { Timeline, TimelineItem }  from 'vertical-timeline-component-for-react'

const TimelineView = (props) => {
  return (
    <>
      <Timeline lineColor={'black'}>
        {props.countList && props.countList.map((item, i) => (
          <TimelineItem
            key={i}
            dateText={props.detailDateString[i]}
            style={{ color: '#e86971' }}
          >
            <h3>{props.detailTitle[i]}</h3>
            <p>
              {props.detailContent[i]}
            </p>
          </TimelineItem>
        ))}
      </Timeline>
    </>
  )
}

export default TimelineView

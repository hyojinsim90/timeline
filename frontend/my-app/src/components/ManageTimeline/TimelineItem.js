import React, { useState, useEffect } from "react"
import styled from "styled-components"
import ModifyTimeline from "./Sections/ModifyTimeline"
import Axios from "axios"
import { useParams } from "react-router-dom"

const TimelineItemDiv = styled.div`
  padding: 3rem 2rem;
`

const TimelineItem = (props) => {
  const [timeline, setTimeline] = useState([])
  const [detail, setDetail] = useState([])

  const params = useParams()

  useEffect(() => {
    if(props.user.userData !== undefined && props.user.userData.email !== undefined) {
      Axios.get(`/timeline/master/${props.user.userData.email}`)
        .then(res => {
          if(res.data) {
            const list = res.data.filter(item => item.id.toString() === params.timelineId)
            setTimeline(list)

            Axios.get(`/timeline/detail/${list[0].id}`)
              .then(response => {
                if(response.data) {
                  setDetail(response.data)
                }
              })
          }
        })
    }
  }, [props.user])

  return (
    <TimelineItemDiv>
      <ModifyTimeline timeline={timeline} detail={detail} timelineId={params.timelineId} />
    </TimelineItemDiv>
  )
}

export default TimelineItem

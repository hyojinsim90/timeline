import React, { useState, useEffect } from "react"
import SearchWord from "./Sections/SearchWord"
import SearchButton from "./Sections/SearchButton"
import AllTimeline from "./Sections/AllTimeline"
import styled from "styled-components"
import { Divider } from "antd"
import Axios from "axios"

const TimelineSearchDiv = styled.div`
  padding: 3rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`

const TimelineSearch = () => {
  const [data, setData] = useState([])

  useEffect(() => {
    Axios.get("/timeline/master/list")
      .then(res => {
        if(res.data) {
          setData(res.data)
        }
      })
  }, [])

  return (
    <TimelineSearchDiv>
      <SearchWord />
      <SearchButton />
      <Divider />
      <AllTimeline data={data} />
    </TimelineSearchDiv>
  )
}

export default TimelineSearch

import React from "react"
import SearchWord from "./Sections/SearchWord"
import SearchButton from "./Sections/SearchButton"
import styled from "styled-components"
import { Divider } from "antd"

const TimelineSearchDiv = styled.div`
  padding: 3rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`

const TimelineSearch = () => {
  return (
    <TimelineSearchDiv>
      <SearchWord />
      <SearchButton />
      <Divider />
    </TimelineSearchDiv>
  )
}

export default TimelineSearch

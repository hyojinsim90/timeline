import React from "react"
import styled from "styled-components"
import { Link } from "react-scroll"

const SideScrollDiv = styled.div`
  width: 10%;
  position: fixed;
  right: 5rem;
  margin-top: 10px;
`

const SideScroll = (props) => {
  return (
    <SideScrollDiv>
      {props.countList && props.countList.map((item, i) => (
        <div key={i}>
          <Link to={props.id[i] || ""} spy={true} smooth={true}>
            <span>Day {i + 1}</span>
          </Link>
        </div>
      ))}
    </SideScrollDiv>
  )
}

export default SideScroll

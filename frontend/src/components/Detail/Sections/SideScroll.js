import React from "react"
import styled from "styled-components"
import { Link } from "react-scroll"

const SideScrollDiv = styled.div`
  width: 10%;
  position: fixed;
  right: 5rem;
  margin-top: 70px;
`

const SideScroll = (props) => {
  const handleSetActive = (id) => {
    console.log(id);
  }

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

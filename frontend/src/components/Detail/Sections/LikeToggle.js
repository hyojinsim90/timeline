import React, { useState } from "react"
import { Button } from "antd"
import { LikeOutlined, LikeFilled } from "@ant-design/icons"
import styled from "styled-components"
import Axios from "axios"

const LikeToggleDiv = styled.div`
  margin-top: 4rem;
  button {
    border: none;
    color: #40a9ff;
    svg {
      width: 25px;
      height: 25px;
    }
  }
  button:hover, button:focus, button:active, button::after {
    outline: none;
    border: none;
    box-shadow: none !important;
  }
`
const LikeToggle = (props) => {

  return (
    <LikeToggleDiv>
      <Button onClick={props.onToggleLike}>
        { props.toggle ?
          <div>
            <LikeFilled />
            <span>{props.likeCount}</span>
          </div>
          :
          <div>
            <LikeOutlined />
            <span>{props.likeCount}</span>
          </div>
        }
      </Button>
    </LikeToggleDiv>
  )
}

export default LikeToggle

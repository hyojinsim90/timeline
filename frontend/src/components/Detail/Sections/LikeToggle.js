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
  const [toggle, setToggle] = useState(false)

  const onToggleLike = () => {
    // 로그인한 상태이고 email 값 있을 때
    if(props.cookies.tl_e) {

      const variables = {
        masterId: props.param.timelineId,
        // memberId:
      }

      // 추천한 상태에서 추천 취소할 때 Icon full->empty
      if(toggle) {
        setToggle(false)
        // Axios.post("/timeline/like/change", variables)
      // 추천 안 한 상태에서 추천할 때 Icon emtpy->full
      } else {
        setToggle(true)
      }
    }
  }

  return (
    <LikeToggleDiv>
      <Button onClick={onToggleLike}>
        { toggle ?
          <LikeFilled />
          :
          <LikeOutlined />
        }
      </Button>
    </LikeToggleDiv>
  )
}

export default LikeToggle

import React, { useState, useEffect } from "react"
import styled from "styled-components"
import { Divider } from "antd"
import ViewsTop from "./Sections/ViewsTop"
import LikesTop from "./Sections/LikesTop"
import Axios from "axios"

const MainDiv = styled.div`
  padding: 3rem 0;
`

const Main = () => {
  const [viewsList, setViewsList] = useState([])
  const [likesList, setLikesList] = useState([])

  useEffect(() => {
    // 조회수 TOP 10
    Axios.get("/timeline/master/list/view")
      .then(res => {
        if(res.data) {
          setViewsList(res.data)
        }
      })

    // 추천수 TOP 10
    Axios.get("/timeline/master/list/like")
      .then(res => {
        if(res.data) {
          setLikesList(res.data)
        }
      })
  }, [])

  return (
    <MainDiv>
      <ViewsTop viewsList={viewsList} />
      <Divider />
      <LikesTop likesList={likesList} />
    </MainDiv>
  )
}

export default Main

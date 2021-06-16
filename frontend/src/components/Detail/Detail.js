import React, { useState, useEffect } from "react"
import TopMaster from "./Sections/TopMaster"
import MiddleDetail from "./Sections/MiddleDetail"
import BottomComment from "./Sections/BottomComment"
import styled from "styled-components"
import { useParams } from "react-router-dom"
import Axios from "axios"

const DetailDiv = styled.div`
  margin: 3rem 5rem;
`

const Detail = (props) => {
  const [masterData, setMasterData] = useState([])
  const [nickname, setNickname] = useState("")
  const param = useParams()

  useEffect(() => {
    Axios.get(`/timeline/master/list`)
      .then(res => {
        if(res.data) {
          const data = res.data.filter(item => item.id.toString() === param.timelineId)
          setMasterData(data[0])
          Axios.get(`/auth/nicknames/${data[0].author}`)
            .then(res => {
              setNickname(res.data.nickname)
            })
        }
      })
  }, [param])

  return (
    <DetailDiv>
      <TopMaster masterData={masterData} nickname={nickname} />
    </DetailDiv>
  )
}

export default Detail

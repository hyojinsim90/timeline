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
  const [countList, setCountList] = useState([])
  const [detailDateString, setDetailDateString] = useState([])
  const [title, setTitle] = useState([])
  const [content, setContent] = useState([])
  const param = useParams()

  useEffect(() => {
    let detailDatestringArr = [...detailDateString]
    let titleArr = [...title]
    let contentArr = [...content]

    // timeline master 가져오기
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

    // timeline detail 가져오기
    Axios.get(`/timeline/detail/${param.timelineId}`)
      .then(res => {
        if(res.data) {
          setCountList(Object.keys(res.data))
          setDetailDateString(res.data.scheduleDate)
          res.data.forEach((item, i) => {
            detailDatestringArr[i] = item.createdDate.substring(0, 10)
            titleArr[i] = item.title
            contentArr[i] = item.content

            setDetailDateString(detailDatestringArr)
            setTitle(titleArr)
            setContent(contentArr)
          })

        }
      })
  }, [param])

  return (
    <DetailDiv>
      <TopMaster masterData={masterData} nickname={nickname} />
      <MiddleDetail countList={countList} detailDateString={detailDateString} title={title} content={content} />
    </DetailDiv>
  )
}

export default Detail

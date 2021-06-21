import React, { useState, useEffect } from "react"
import TopMaster from "./Sections/TopMaster"
import MiddleDetail from "./Sections/MiddleDetail"
import BottomComment from "./Sections/BottomComment"
import SideScroll from "./Sections/SideScroll"
import LikeToggle from "./Sections/LikeToggle"
import styled from "styled-components"
import { useParams } from "react-router-dom"
import Axios from "axios"
import { useCookies } from "react-cookie"

const DetailDiv = styled.div`
  margin: 3rem 5rem;
`

const MiddleDiv = styled.div`
  width: 100%;
  display: flex;
`

const Detail = (props) => {
  const [masterData, setMasterData] = useState([])
  const [nickname, setNickname] = useState("")
  const [countList, setCountList] = useState([])
  const [detailDateString, setDetailDateString] = useState([])
  const [title, setTitle] = useState([])
  const [content, setContent] = useState([])
  const [id, setId] = useState([])
  const [loginStatus, setLoginStatus] = useState(false)

  const param = useParams()
  const [cookies, setCookie, removeCookie] = useCookies([])

  useEffect(() => {
    let detailDatestringArr = [...detailDateString]
    let titleArr = [...title]
    let contentArr = [...content]
    let idArr = [...id]

    // timeline master 가져오기
    Axios.get(`/timeline/master/list`)
      .then(res => {
        if(res.data) {
          const data = res.data.filter(item => item.id.toString() === param.timelineId)
          setMasterData(data[0])
          console.log(data[0]);
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
            idArr[i] = item.id.toString()
          })
          setDetailDateString(detailDatestringArr)
          setTitle(titleArr)
          setContent(contentArr)
          setId(idArr)
        }
      })

      // 로그인했을 시 상태값 true
      if(cookies.tl_e && cookies.tl_token && cookies.tl_re && cookies.tl_exp) {
        setLoginStatus(true)
      }
  }, [param])

  return (
    <DetailDiv>
      <TopMaster masterData={masterData} nickname={nickname} />
      <MiddleDiv>
        <MiddleDetail countList={countList} detailDateString={detailDateString} title={title} content={content} id={id} />
        <SideScroll countList={countList} id={id} />
      </MiddleDiv>
      { /* 로그인 시에만 추천 버튼 노출 */ }
      { loginStatus &&
        <LikeToggle cookies={cookies} param={param} />
      }
      <BottomComment />
    </DetailDiv>
  )
}

export default Detail

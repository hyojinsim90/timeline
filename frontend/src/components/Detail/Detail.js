import React, { useState, useEffect } from "react"
import TopMaster from "./Sections/TopMaster"
import MiddleDetail from "./Sections/MiddleDetail"
import BottomComment from "./Sections/BottomComment"
import SideScroll from "./Sections/SideScroll"
import LikeToggle from "./Sections/LikeToggle"
import styled from "styled-components"
import { useParams } from "react-router-dom"
import Axios from "axios"

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
  const [comment, setComment] = useState([])
  const [toggle, setToggle] = useState(false)
  const [likeCount, setLikeCount] = useState(0)

  const param = useParams()

  useEffect(() => {
    let detailDatestringArr = [...detailDateString]
    let titleArr = [...title]
    let contentArr = [...content]
    let idArr = [...id]
    let refineDate = []

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
            refineDate[i] = item.scheduleDate
            titleArr[i] = item.title
            contentArr[i] = item.content
            idArr[i] = item.id.toString()
          })

          // scheduleDate에 - 문자열 붙이기
          refineDate.forEach((item, i) => {
            detailDatestringArr[i] = item.substring(0, 4) + "-" + item.substring(4, 6) + "-" + item.substring(6, 8)
          })

          setDetailDateString(detailDatestringArr)
          setTitle(titleArr)
          setContent(contentArr)
          setId(idArr)
        }
      })

      Axios.get("/timeline/comment/list")
        .then(res => {
          const data = res.data.filter(item => item.masterId.toString() === param.timelineId)
          setComment(data)
        })

      // 로그인했을 시 상태값 true
      if(props.user.userData !== undefined) {
        setLoginStatus(true)
      }
  }, [param, comment])

  useEffect(() => {
    if(props.user.userData !== undefined) {
      // 렌더링 되자 마자 likeCount 가져오기
      Axios.get("/timeline/like/list")
        .then(res => {
          const data = res.data.filter(item => item.masterId.toString() === param.timelineId)
          setLikeCount(data.length)
          Axios.get(`/auth/getId/${props.user.userData.email}`)
            .then(userRes => {
              if(userRes.data) {
                const checkMyLikes = data.filter(item => item.memberId === userRes.data)
                // 현재 유저가 like 추천한 글인지 체크 후 아이콘(empty, full) 세팅
                if(checkMyLikes.length !== 0) {
                  setToggle(true)
                }
              }
            })
        })
    }
  }, [props.user])

  const onToggleLike = () => {
    // 로그인한 상태이고 email 값 있을 때
    if(props.user.userData.email) {
      Axios.get(`/auth/getId/${props.user.userData.email}`)
        .then(res => {
          if(res.data) {
            const variables = {
              masterId: parseInt(param.timelineId),
              memberId: res.data
            }

            // 추천한 상태에서 추천 취소할 때 Icon full->empty
            if(toggle) {
              Axios.post("/timeline/like/change", variables)
                .then(res => {
                  if(res.data) {
                    setLikeCount(res.data.likeCount)
                    setToggle(false)
                  }
                })
              // Axios.post("/timeline/like/change", variables)
            // 추천 안 한 상태에서 추천할 때 Icon emtpy->full
            } else {
              Axios.post("/timeline/like/change", variables)
                .then(res => {
                  if(res.data) {
                    setLikeCount(res.data.likeCount)
                    setToggle(true)
                  }
                })
            }

          }
        })
    }
  }

  return (
    <DetailDiv>
      <TopMaster masterData={masterData} nickname={nickname} />
      <MiddleDiv>
        <MiddleDetail countList={countList} detailDateString={detailDateString} title={title} content={content} id={id} />
        <SideScroll countList={countList} id={id} />
      </MiddleDiv>
      { /* 로그인 시에만 추천 버튼 노출 */ }
      { loginStatus &&
        <LikeToggle toggle={toggle} onToggleLike={onToggleLike} likeCount={likeCount} />
      }
      <BottomComment param={param} comment={comment} user={props.user.userData} />
    </DetailDiv>
  )
}

export default Detail

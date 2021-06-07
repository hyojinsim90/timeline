import React, { useState, useEffect } from "react"
import MenuBar from "./Sections/MenuBar"
import { Row, Col, Card, Button } from "antd"
import styled from "styled-components"
import { Link } from "react-router-dom"
import SomeTimeline from "./Sections/SomeTimeline"
import { useSelector } from "react-redux"
import Axios from "axios"

const TimelineDiv = styled.div`
  padding: 3rem 2rem;
  .ant-card {
    text-align: left;
    margin-bottom: 3rem;
    font-size: 17px;
    .ant-card-head-title {
      font-weight: bold;
      font-size: 17px;
    }
  }
`

const MyTimeline = () => {
  const [list, setList] = useState([])

  const user = useSelector(state => state.user)

  useEffect(() => {

    if(user.userData !== undefined && user.userData.email !== undefined) {
      Axios.get(`/timeline/master/${user.userData.email}`)
        .then(res => {
          if(res.data) {
            const latestData = res.data.slice(-3)
            setList(latestData)
          }
        })
    }
  }, [user])

  return (
    <div>
      <MenuBar />
      <TimelineDiv>
        <Row gutter={16}>
          <Col span={24}>
            <Card title="내 타임라인" bordered={false} extra={<Link to="/createtimeline"><Button size="large">생성하기</Button></Link>}>
              <SomeTimeline list={list} />
            </Card>
          </Col>
          <Col span={24}>
            <Card title="관심 타임라인" bordered={false}>
              관심 타임라인이 없습니다
            </Card>
          </Col>
          <Col span={24}>
            <Card title="추천 타임라인" bordered={false}>
              추천 타임라인이 없습니다
            </Card>
          </Col>
        </Row>
      </TimelineDiv>
    </div>
  )
}

export default MyTimeline

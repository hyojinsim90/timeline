import React, { useState, useEffect } from "react"
import MenuBar from "./Sections/MenuBar"
import { Row, Col, Card, Button } from "antd"
import styled from "styled-components"
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

const MyTimeline = (props) => {
  const [allList, setAllList] = useState([])
  const [list, setList] = useState([])

  useEffect(() => {

    if(props.user.userData !== undefined && props.user.userData.email !== undefined) {
      Axios.get(`/timeline/master/${props.user.userData.email}`)
        .then(res => {
          if(res.data) {
            const latestData = res.data.slice(-3)
            setAllList(res.data)
            setList(latestData)
          }
        })
    }
  }, [props.user])

  const onMoveToCreate = () => {
    // 타임라인 최대 저장 개수 20개로 제한 => 20개 초과하면 페이지 접근 x
    if(allList.length > 19) {
      alert("타임라인은 최대 20개까지 작성할 수 있습니다")
    } else {
      props.history.push("/createtimeline")
    }
  }

  return (
    <div>
      <MenuBar />
      <TimelineDiv>
        <Row gutter={16}>
          <Col span={24}>
            <Card title="내 타임라인" bordered={false} extra={<Button size="large" onClick={onMoveToCreate}>생성하기</Button>}>
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

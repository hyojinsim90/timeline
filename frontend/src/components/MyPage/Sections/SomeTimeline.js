import React, { useEffect } from "react"
import { Card, Button, List, Table, Tag } from "antd"
import { Link } from "react-router-dom"
import { PlusOutlined } from "@ant-design/icons"
import Axios from "axios"

const SomeTimeline = () => {

  useEffect(() => {
    Axios.get("/timeline/master/list")
      .then(res => {

      })
  }, [])

  return (
    <>
      <Card title="" bordered={false} extra={<Link to="/timelinelist"><Button><PlusOutlined />더 보기</Button></Link>}>
        <List
          itemLayout="horizontal"
        >
          <List.Item>
            <List.Item.Meta
              title="제목"
            />
            <List.Item.Meta
              title="분야"
            />
            <List.Item.Meta
              title="진행완료"
            />
            <List.Item.Meta
              title="비공개"
            />
            <List.Item.Meta
              title="조회수"
            />
            <List.Item.Meta
              title="추천수"
            />
            <List.Item.Meta
              title="클래스 요청수"
            />
            <List.Item.Meta
              title="20210602"
            />
          </List.Item>
        </List>
      </Card>
    </>
  )
}

export default SomeTimeline

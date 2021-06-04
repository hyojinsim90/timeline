import React, { useState, useEffect } from "react"
import { Card, Button, List, Table, Tag } from "antd"
import { Link } from "react-router-dom"
import { PlusOutlined } from "@ant-design/icons"
import Axios from "axios"

const SomeTimeline = () => {
  const data = [
    {
      "author": "b@gmail.com",
      "category": "경제",
      "complete": true,
      "imgFilePath": "test",
      "open": true,
      "title": "test",
      "likeCount": 0,
      "reqCount": 0,
      "viewCount": 0,
      "createDate": "20210601"
    },
    {
      "author": "b@gmail.com",
      "category": "경제",
      "complete": true,
      "imgFilePath": "test",
      "open": true,
      "title": "test2",
      "likeCount": 0,
      "reqCount": 0,
      "viewCount": 0,
      "createDate": "20210602"
    },
    {
      "author": "b@gmail.com",
      "category": "경제",
      "complete": true,
      "imgFilePath": "test",
      "open": true,
      "title": "test3",
      "likeCount": 0,
      "reqCount": 0,
      "viewCount": 0,
      "createDate": "20210603"
    },
    {
      "author": "b@gmail.com",
      "category": "경제",
      "complete": true,
      "imgFilePath": "test",
      "open": true,
      "title": "test4",
      "likeCount": 0,
      "reqCount": 0,
      "viewCount": 0,
      "createDate": "20210604"
    },
  ]

  const [list, setList] = useState([])
  useEffect(() => {
    // Axios.get("/timeline/master/list")
    //   .then(res => {
    //
    //   })

    const latestData = data.slice(-3)
    setList(latestData)
  }, [])

  return (
    <>
      <Card title="" bordered={false} extra={<Link to="/timelinelist"><Button><PlusOutlined />더 보기</Button></Link>}>
        <List
          itemLayout="horizontal"
        >
          { list && list.map((item, i) => (
            <List.Item key={i}>
              <List.Item.Meta
                title={item.title}
              />
              <List.Item.Meta
                title={item.category}
              />
              <List.Item.Meta
                title={item.complete ? '진행완료' : '진행중'}
              />
              <List.Item.Meta
                title={item.open ? '공개' : '비공개'}
              />
              <List.Item.Meta
                title={item.viewCount.toString()}
              />
              <List.Item.Meta
                title={item.likeCount.toString()}
              />
              <List.Item.Meta
                title={item.reqCount.toString()}
              />
              <List.Item.Meta
                title={item.createDate}
              />
            </List.Item>
          ))}
        </List>
      </Card>
    </>
  )
}

export default SomeTimeline

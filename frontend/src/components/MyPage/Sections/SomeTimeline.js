import React, { useState } from "react"
import { Card, Button, List, Table, Tag } from "antd"
import { Link } from "react-router-dom"
import { PlusOutlined } from "@ant-design/icons"

const SomeTimeline = () => {
  const [tag, setTag] = useState(true)
  const columns = [
    {
      title: '제목',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: '분야',
      dataIndex: 'category',
      key: 'category',
    },
    {
      title: '진행여부',
      dataIndex: 'complete',
      key: 'complete',
      render: tag => <Tag color="blue">진행중</Tag>,
    },
    {
      title: '공개여부',
      dataIndex: 'open',
      key: 'open',
      render: tag => <Tag color="lightgray">비공개</Tag>,
    },
    {
      title: '조회수',
      dataIndex: 'views',
      key: 'views',
    },
    {
      title: '추천수',
      dataIndex: 'likes',
      key: 'likes',
    },
    {
      title: '클래스 요청수',
      dataIndex: 'request',
      key: 'request',
    },
    {
      title: '생성일자',
      dataIndex: 'created',
      key: 'created',
    },
  ]

  const data = [
    {
      key: '1',
      title: '제목',
      category: '경제',
      complete: '진행중',
      open: '비공개',
      views: 2000,
      likes: 1000,
      request: 500,
      created: '2021-06-02'
    },
  ];

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
        <Table columns={columns} dataSource={data} />
      </Card>
    </>
  )
}

export default SomeTimeline

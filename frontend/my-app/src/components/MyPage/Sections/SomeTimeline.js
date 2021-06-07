import React from "react"
import { Card, Button, List } from "antd"
import { Link } from "react-router-dom"
import { PlusOutlined } from "@ant-design/icons"

const SomeTimeline = (props) => {
  return (
    <>
      <Card title="" bordered={false} extra={<Link to="/timelinelist"><Button><PlusOutlined />더 보기</Button></Link>}>
        <List
          itemLayout="horizontal"
        >
          { props.list && props.list.map((item, i) => (
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
                title={item.createdDate.substring(0, 10)}
              />
            </List.Item>
          ))}
        </List>
      </Card>
    </>
  )
}

export default SomeTimeline

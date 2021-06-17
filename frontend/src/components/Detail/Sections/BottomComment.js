import React from "react"
import { Space, Comment, Tooltip, Rate, List, Input, Button, Divider } from "antd"
import moment from "moment"
import styled from "styled-components"

const BottomCommentDiv = styled.div`
  margin-top: 150px;
  .ant-space {
    display: flex;
    margin-bottom: 18px;
    h3 {
      margin-bottom: 0;
    }
    & + div {
      display: flex;
      button {
        height: 98px;
        margin-left: 16px;
      }
    }
  }
  .ant-comment-content-detail {
    text-align: left;
  }
  .ant-comment-actions {
    text-align: right;
  }
`

const data = [
  {
    actions: [<span key="comment-list-reply-to-0">수정</span>, <span key="comment-list-reply-to-0">삭제</span>],
    author: 'Han Solo님',
    content: (
      <p>content</p>
    ),
    datetime: (
      <div>
        <span>{moment().format('YYYY-MM-DD')}</span>
        <Rate disabled />
      </div>
    ),
  },
  {
    actions: [<span key="comment-list-reply-to-0">수정</span>, <span key="comment-list-reply-to-0">삭제</span>],
    author: `Han님`,
    content: (
      <p>content</p>
    ),
    datetime: (
      <div>
        <span>{moment().format('YYYY-MM-DD')}</span>
        <Rate disabled />
      </div>
    ),
  },
];

const { TextArea } = Input

const BottomComment = () => {
  return (
    <BottomCommentDiv>
      <Space>
        <h3>댓글 ({data.length})</h3>
        <Rate />
      </Space>
      <div>
        <TextArea
          autoSize={{ minRows: 4, maxRows: 4 }}
        />
        <Button>저장</Button>
      </div>
      <Divider />
      <List
        className="comment-list"
        itemLayout="horizontal"
        dataSource={data}
        renderItem={item => (
          <li>
            <Comment
              actions={item.actions}
              author={item.author}
              content={item.content}
              datetime={item.datetime}
            />
            <Divider />
          </li>
        )}
      />
    </BottomCommentDiv>
  )
}

export default BottomComment

import React, { useState } from "react"
import { Space, Comment, Tooltip, Rate, List, Input, Button, Divider } from "antd"
import moment from "moment"
import styled from "styled-components"
import Axios from "axios"

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
  .ant-comment {
    button {
      border: none;
    }
    button:hover, button:focus, button:active, button::after {
      outline: none;
      border: none;
      box-shadow: none !important;
      color: black;
    }
  }
  .ant-comment-content-author {
    display: flex;
    align-items: center;
    div {
      ul {
        margin-left: 10px;
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

const { TextArea } = Input

const BottomComment = (props) => {
  const [data, setData] = useState([])

  return (
    <BottomCommentDiv>
      <Space>
        <h3>댓글 ({props.comment.length})</h3>
        <Rate onChange={props.onChangeStar} />
      </Space>
      <div>
        <TextArea
          autoSize={{ minRows: 4, maxRows: 4 }}
          onChange={props.onChangeCommentContent}
        />
        <Button onClick={props.onSaveComment}>저장</Button>
      </div>
      <Divider />
      <List
        className="comment-list"
        itemLayout="horizontal"
        dataSource={props.comment}
      >
        {props.comment && props.comment.map((item, i) => (
          <li key={i}>
            <Comment
              actions={props.user !== undefined && props.user.nickname === item.nickname
                ?
                [<Button onClick={props.onModify}><span>수정</span></Button>, <Button onClick={() => props.onDelete(item.content, item.masterId, item.nickname, item.star)}><span>삭제</span></Button>]
                : ""
              }
              author={item.nickname}
              content={(<p>{item.content}</p>)}
              datetime={(
              <div>
                <span>{moment().format('YYYY-MM-DD')}</span>
                <Rate value={item.star} disabled />
              </div>
              )}
            />
            <Divider />
          </li>
        ))}
      </List>
    </BottomCommentDiv>
  )
}

export default BottomComment

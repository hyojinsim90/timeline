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
        { props.hideInput ?
          <></>
          :
          <Rate onChange={props.onChangeStar} />
        }
      </Space>
      { props.hideInput ?
        <></>
        :
        <div>
          <TextArea
            autoSize={{ minRows: 4, maxRows: 4 }}
            onChange={props.onChangeCommentContent}
          />
          <Button onClick={props.onSaveComment}>저장</Button>
        </div>
      }
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
                [<Button onClick={() => props.onModify(i)}><span>수정</span></Button>, <Button onClick={() => props.onDelete(item.content, item.masterId, item.nickname, item.star)}><span>삭제</span></Button>]
                : ""
              }
              author={item.nickname}
              content={(
              props.modifyStatus && i === props.modifyIndex ?
                <TextArea
                  autoSize={{ minRows: 4, maxRows: 4 }}
                  onChange={props.onChangeCommentContent}
                  value={props.modifyStatus && i === props.modifyIndex ? props.commentContent : item.content}
                />
                :
                <p>{item.content}</p>
              )}
              datetime={(
              <div>
                <span>{moment().format('YYYY-MM-DD')}</span>
                <Rate value={props.modifyStatus && i === props.modifyIndex ? props.star : item.star} disabled={props.modifyStatus && i === props.modifyIndex ? false : true} onChange={props.onChangeStar}/>
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

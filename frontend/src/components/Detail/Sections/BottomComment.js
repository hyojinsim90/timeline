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
  const [content, setContent] = useState("")
  const [star, setStar] = useState("")
  const [data, setData] = useState([])

  const onChangeStar = (value) => {
    setStar(value)
  }

  const onChangeContent = (e) => {
    setContent(e.target.value)
  }

  const onSaveComment = () => {
    const variables = {
      content: content,
      masterId: parseInt(props.param.timelineId),
      nickname: props.user.nickname,
      star: star
    }

    // 댓글 내용 유효성 체크
    if(!content || !props.param.timelineId || !star) {
      alert("내용을 입력해 주세요")
      return false
    }

    Axios.post("/timeline/comment", variables)
      .then(res => {
        if(res.status === 200) {
          alert("댓글 작성을 완료했습니다")
        }
      })
  }

  return (
    <BottomCommentDiv>
      <Space>
        <h3>댓글 ({props.comment.length})</h3>
        <Rate onChange={onChangeStar} />
      </Space>
      <div>
        <TextArea
          autoSize={{ minRows: 4, maxRows: 4 }}
          onChange={onChangeContent}
        />
        <Button onClick={onSaveComment}>저장</Button>
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
              actions={props.user !== undefined && props.user.nickname === item.nickname ? [<span key="comment-list-reply-to-0">수정</span>, <span key="comment-list-reply-to-0">삭제</span>] : ""}
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

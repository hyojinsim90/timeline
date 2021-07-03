import React, { useState, useEffect } from "react"
import { Table, Tag } from "antd"
import styled from "styled-components"
import Axios from "axios"
import { useSelector } from "react-redux"
import { Link } from "react-router-dom"
import { DeleteOutlined } from "@ant-design/icons"

const TimelineListDiv = styled.div`
  padding: 3rem 2rem;
  td {
    div {
      div {
        width: 100PX;
        height: 100PX;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      img {
        width: 100px;
        height: 100px;
      }
    }
    .ant-empty {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
`

const TimelineList = (props) => {
  const [list, setList] = useState([])
  const [checkDelete, setCheckDelete] = useState(false)

  const columns = [
    {
      title: '이미지',
      dataIndex: 'imgFullPath',
      key: 'imgFullPath',
      render: imgFullPath => (
        <div>
          { imgFullPath ?
            <img src={imgFullPath} />
          :
            <div>
              NO IMAGE
            </div>
          }
        </div>
      )
    },
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
      render: complete => (
        <div>
          { complete ?
            <Tag color="mediumpurple">진행완료</Tag>
            :
            <Tag color="skyblue">진행중</Tag>
          }
        </div>
      ),
    },
    {
      title: '공개여부',
      dataIndex: 'open',
      key: 'open',
      render: open => (
        <div>
          { open ?
            <Tag color="black">공개</Tag>
            :
            <Tag color="lightgray">비공개</Tag>
          }
        </div>
      )
    },
    {
      title: '조회수',
      dataIndex: 'viewCount',
      key: 'viewCount',
    },
    {
      title: '추천수',
      dataIndex: 'likeCount',
      key: 'likeCount',
    },
    {
      title: '생성일자',
      dataIndex: 'createdDate',
      key: 'createdDate',
      render: date => (
        <div>
          {date.substring(0, 10)}
        </div>
      )
    },
    {
      title: '삭제',
      dataIndex: 'id',
      key: 'id',
      render: (id) => (
        <div>
          <DeleteOutlined onClick={(e) => onDeleteTimeline(e, id)} />
        </div>
      )
    },
 ]

  useEffect(() => {
    if(props.user.userData !== undefined && props.user.userData.email !== undefined) {
      Axios.get(`/timeline/master/${props.user.userData.email}`)
        .then(res => {
          if(res.data) {
            setList(res.data)
          }
        })
    }
  }, [props.user, checkDelete])

  const onDeleteTimeline = (e, id) => {
    // onRow click event 실행 막기
    e.stopPropagation()

    Axios.delete(`/timeline/${id}`)
      .then(res => {
        if(res.status === 200) {
          alert("삭제되었습니다")
          setCheckDelete(!checkDelete)
        }
      })
  }

  return (
    <TimelineListDiv>
      <Table columns={columns} dataSource={list} rowKey={list => list.id} pagination={false}
        onRow={(record, rowIndex) => {
        return {
          onClick: () => {
            props.history.push(`/timelinelist/${record.id}`)
          },
        }
      }}
      />
    </TimelineListDiv>
  )
}

export default TimelineList

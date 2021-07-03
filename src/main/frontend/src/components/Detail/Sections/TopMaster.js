import React, { useEffect } from "react"
import { Card, Tag } from "antd"
import { EyeOutlined, LikeOutlined } from "@ant-design/icons"
import styled from "styled-components"

const TopMasterDiv = styled.div`
  .ant-card-body {
    & > div {
      display: flex;
      justify-content: space-between;
      align-items: flex-end;
      /* 제목, 닉네임 */
      div:nth-child {
        display: flex;
        flex-direction: column;
        align-items: center;
      }
      /* 조회수, 추천수 */
      div:last-child {
        display: flex;
        div {
          margin-left: 5px;
          display: flex;
          align-items: center;
          span {
            margin-left: 2px;
          }
        }
      }
    }
    .ant-card-meta {
      .ant-card-meta-title {
        text-align: left;
        font-size: 32px;
        font-weight: bold;
        margin-bottom: 20px;
      }
      & + div {
        justify-content: center;
        span {
          font-size: 18px;
          color: mediumpurple;
        }
      }
    }
  }
`

const { Meta } = Card

const TopMaster = (props) => {
  useEffect(() => {

  })
  return (
    <TopMasterDiv>
      {props.masterData &&
        <Card>
          <div>
            <div>
              <Tag color="black">{props.masterData.category}</Tag>
              {props.masterData.complete ?
                <Tag color="mediumpurple">
                  <span>진행완료</span>
                </Tag>
                :
                <Tag color="skyblue">
                  <span>진행중</span>
                </Tag>
              }
            </div>
            <div>
              <Meta
                title={props.masterData.title}
              />
              <div>
                <span>{props.nickname}</span>
              </div>
            </div>
            <div>
              <div>
                <EyeOutlined />
                <span>{props.masterData.viewCount}</span>
              </div>
              <div>
                <LikeOutlined />
                <span>{props.masterData.likeCount}</span>
              </div>
            </div>
          </div>
        </Card>
      }
    </TopMasterDiv>
  )
}

export default TopMaster

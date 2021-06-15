import React, { useState } from "react"
import { Card, Tag } from "antd"
import styled from "styled-components"
import { Swiper, SwiperSlide } from "swiper/react"
import "swiper/swiper.min.css";
import "swiper/components/pagination/pagination.min.css"
import "swiper/components/navigation/navigation.min.css"
import SwiperCore, { Pagination,Navigation } from "swiper/core"
import { EyeOutlined, LikeOutlined } from "@ant-design/icons"
import { Link } from "react-router-dom"

const LikesTopDiv = styled.div`
  margin: 0px 40px;
  h2 {
    text-align: left;
    margin-bottom: 0;
  }
  .swiper-container {
    height: 100%;
    .swiper-wrapper {
      .ant-tag:first-child {
        position: absolute;
        top: 5px;
        left: 5px;
      }
      .ant-tag:nth-child(2) {
        position: absolute;

      }
      margin-top: 55px;
      .ant-card-meta-title {
        text-align: left;
      }
    }
    .ant-card-cover img {
      width: 100%;
      height: 150px;
    }
    .swiper-button-next {
      top: 10%;
    }
    .swiper-button-prev {
      top: 10%;
      position: absolute;
      left: 94%;
    }
    .swiper-button-next:after, .swiper-button-prev:after {
      font-size: 15px;
      font-weight: bold;
    }
    .ant-card-meta + div {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      margin-top: 20px;
      div {
        display: flex;
        align-items: center;
        margin-right: 3px;
        span {
          margin-right: 2px;
          font-size: 15px;
        }
      }
      & + .ant-tag {
        width: 60px;
        height: 60px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 50%;
        position: absolute;
        top: 120px;
        right: 0;
      }
    }
  }
`

SwiperCore.use([Pagination,Navigation])

const { Meta } = Card

const LikesTop = (props) => {
  return (
    <LikesTopDiv>
    <h2>추천 타임라인 TOP 10</h2>
      <Swiper
        slidesPerView={5}
        centeredSlides={false}
        spaceBetween={15}
        navigation={{nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev'}}
      >
      <div className="swiper-button-prev swiper-button-black"></div>
      <div className="swiper-button-next swiper-button-black"></div>
      {props.likesList && props.likesList.map((item, i) => (
        <SwiperSlide key={i}>
          <Link to={`/${item.id}`}>
            <Card
              style={{ width: '100%' }}
              cover={
                <img
                  alt="img"
                  src={props.likesList[i].imgFullPath}
                />
              }
            >
              <Tag color="black">{props.likesList[i].category}</Tag>
              <Meta
                title={props.likesList[i].title}
              />

              <div>
                <div>
                  <EyeOutlined />
                  <span>{props.likesList[i].viewCount}</span>
                </div>
                <div>
                  <LikeOutlined />
                  <span>{props.likesList[i].likeCount}</span>
                </div>
              </div>
              {props.likesList[i].complete ?
                <Tag color="mediumpurple">
                  <span>진행완료</span>
                </Tag>
                :
                <Tag color="skyblue">
                  <span>진행중</span>
                </Tag>
              }
            </Card>
          </Link>
        </SwiperSlide>
      ))}
      </Swiper>
    </LikesTopDiv>
  )
}

export default LikesTop

import React, { useState } from "react"
import { Card, Tag } from "antd"
import styled from "styled-components"
import { Swiper, SwiperSlide } from "swiper/react"
import "swiper/swiper.min.css";
import "swiper/components/pagination/pagination.min.css"
import "swiper/components/navigation/navigation.min.css"
import SwiperCore, { Pagination,Navigation } from "swiper/core"
import { EyeOutlined, LikeOutlined } from "@ant-design/icons"

const ViewsTopDiv = styled.div`
  margin: 0px 40px;
  h2 {
    text-align: left;
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
    }
  }
`

SwiperCore.use([Pagination,Navigation])

const { Meta } = Card

const ViewsTop = (props) => {
  const [swiperRef, setSwiperRef] = useState("")
  return (
    <ViewsTopDiv>
    <h2>오늘의 추천 TOP 100</h2>
      <Swiper
        slidesPerView={5}
        centeredSlides={false}
        spaceBetween={15}
        navigation={{nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev'}}
      >
      <div className="swiper-button-prev swiper-button-black"></div>
      <div className="swiper-button-next swiper-button-black"></div>
      {props.viewsList && props.viewsList.map((item, i) => (
        <SwiperSlide key={i}>
          <Card
            style={{ width: '100%' }}
            cover={
              <img
                alt="img"
                src={props.viewsList[i].imgFullPath}
              />
            }
          >
            <Tag color="black">{props.viewsList[i].category}</Tag>
            <Meta
              title={props.viewsList[i].title}
            />
            {props.viewsList[i].complete ?
              <Tag color="mediumpurple">진행완료</Tag>
              :
              <Tag color="skyblue">진행중</Tag>
            }
            <div>
              <div>
                <EyeOutlined />
                <span>{props.viewsList[i].viewCount}</span>
              </div>
              <div>
                <LikeOutlined />
                <span>{props.viewsList[i].likeCount}</span>
              </div>
            </div>
          </Card>
        </SwiperSlide>
      ))}
      </Swiper>
    </ViewsTopDiv>
  )
}

export default ViewsTop

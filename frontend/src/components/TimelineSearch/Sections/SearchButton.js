import React, { useState, useEffect } from "react"
import styled from "styled-components"
import { Swiper, SwiperSlide } from "swiper/react"
import "swiper/swiper.min.css"
import "swiper/components/pagination/pagination.min.css"
import "swiper/components/navigation/navigation.min.css"
import SwiperCore, { Navigation } from "swiper/core"
import { CheckOutlined } from "@ant-design/icons"

const SearchButtonDiv = styled.div`
  padding: 3rem 0;
  width: 100%;
  .swiper-button-next, .swiper-button-prev {
    color: black;
  }
  .swiper-button-next {
    padding-left: 30px;
  }
  .swiper-button-prev {
    padding-right: 30px;
  }
  .swiper-button-next:after, .swiper-button-prev:after {
    font-size: 17px;
    font-weight: bold;
  }
  .swiper-slide {
    display: flex;
    flex-direction: column;
    align-items: center;
    div {
      width: 62px;
      height: 62px;
      border-radius: 50%;
      background-color: rgb(240, 243, 243);
      display: flex;
      -webkit-box-pack: center;
      justify-content: center;
      -webkit-box-align: center;
      align-items: center;
      position: relative;
      span {
        color: white;
      }
    }
    div.checked {
      content: "";
      inset: 0px;
      border-radius: 50%;
      background-color: rgb(23, 161, 255);
      mix-blend-mode: normal;
      opacity: 0.8;
    }
  }
`

const SearchButton = () => {
  const [checkStatus, setCheckStatus] = useState([])

  useEffect(() => {
    let arr = []
    arr[0] = "checked"
    setCheckStatus(arr)
  }, [])

  const onTest = (e) => {

    console.log("ab");
  }

  return (
    <SearchButtonDiv>
      <Swiper
        slidesPerView={8}
        navigation={true}
      >
        <SwiperSlide>
          <div className={checkStatus[0]} onClick={onTest}>
            <CheckOutlined />
          </div>
          <span>전체</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>생활</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>여행</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>문화</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>경제</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>기타</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>test</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>test</span>
        </SwiperSlide>
        <SwiperSlide>
          <div>

          </div>
          <span>test</span>
        </SwiperSlide>
      </Swiper>
    </SearchButtonDiv>
  )
}

export default SearchButton

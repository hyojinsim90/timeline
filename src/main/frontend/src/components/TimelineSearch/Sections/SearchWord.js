import React from "react"
import { Input } from "antd"
import { SearchOutlined } from "@ant-design/icons"
import styled from "styled-components"

const SearchWordDiv = styled.div`
  width: 250px;
  display: flex;
  align-items: center;
  h3 {
    font-weight: bold;
    width: 80px;
    margin-bottom: 0;
  }
  .ant-input {
    border-bottom: 1px solid black;
  }
`

const SearchWord = () => {
  return (
    <SearchWordDiv>
      <h3>검색</h3>
      <SearchOutlined />
      <Input bordered={false} />
    </SearchWordDiv>
  )
}

export default SearchWord

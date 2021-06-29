import React from "react"
import { Pagination } from "antd"
import styled from "styled-components"

const AllTimeline = (props) => {
  return (
    <>
      <Pagination
        showSizeChanger={false}
        total={200}
        defaultCurrent={1}
        pageSize={20}
      />
    </>
  )
}

export default AllTimeline

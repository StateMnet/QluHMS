import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/hotel/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderId) {
  return request({
    url: '/hotel/order/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/hotel/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/hotel/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderIds) {
  return request({
    url: '/hotel/order/' + orderIds,
    method: 'delete'
  })
}

// 订单状态修改
export function changeOrderStatus(orderId, status) {
  const data = {
    orderId,
    status
  }
  return request({
    url: '/hotel/order/changeStatus',
    method: 'put',
    data: data
  })
} 
import request from '@/utils/request'

// 查询房间列表
export function listRoom(query) {
  return request({
    url: '/hotel/room/list',
    method: 'get',
    params: query
  })
}

// 查询房间详细
export function getRoom(roomId) {
  return request({
    url: '/hotel/room/' + roomId,
    method: 'get'
  })
}

// 查询房间详细
export function getRoomType() {
  return request({
    url: '/hotel/room/getAllType' ,
    method: 'get'
  })
}

// 新增房间
export function addRoom(data) {
  return request({
    url: '/hotel/room',
    method: 'post',
    data: data
  })
}

// 修改房间
export function updateRoom(data) {
  return request({
    url: '/hotel/room',
    method: 'put',
    data: data
  })
}

// 删除房间
export function delRoom(roomIds) {
  return request({
    url: '/hotel/room/' + roomIds,
    method: 'delete'
  })
}

// 导出房间
export function exportRoom(query) {
  return request({
    url: '/hotel/room/export',
    method: 'get',
    params: query
  })
}

// 房间状态修改
export function changeRoomStatus(roomId, status) {
  const data = {
    roomId,
    status
  }
  return request({
    url: '/hotel/room/changeStatus',
    method: 'put',
    data: data
  })
}

import request from '@/utils/request'

// 查询房型列表
export function listType(query) {
  return request({
    url: '/hotel/type/list',
    method: 'get',
    params: query
  })
}

// 查询房型详细
export function getType(typeId) {
  return request({
    url: '/hotel/type/' + typeId,
    method: 'get'
  })
}

// 新增房型
export function addType(data) {
  return request({
    url: '/hotel/type',
    method: 'post',
    data: data
  })
}

// 修改房型
export function updateType(data) {
  return request({
    url: '/hotel/type',
    method: 'put',
    data: data
  })
}

// 删除房型
export function delType(typeIds) {
  return request({
    url: '/hotel/type/' + typeIds,
    method: 'delete'
  })
}

// 导出房型
export function exportType(query) {
  return request({
    url: '/hotel/type/export',
    method: 'get',
    params: query
  })
}

// 获取所有房型（下拉用）
export function getAllTypes() {
  return request({
    url: '/hotel/type/all',
    method: 'get'
  })
} 
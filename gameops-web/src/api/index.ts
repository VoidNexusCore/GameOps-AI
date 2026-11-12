import http from './http'

export const getOverview = () => http.get('/dashboard/overview')
export const getPlayers = (params: Record<string, unknown>) => http.get('/players', { params })
export const updatePlayerStatus = (id: number, status: number) => http.put(`/players/${id}/status`, { status })
export const getOrders = (params: Record<string, unknown>) => http.get('/orders', { params })
export const getActivities = () => http.get('/activities')
export const createActivity = (data: Record<string, unknown>) => http.post('/activities', data)
export const sendAiMessage = (message: string) => http.post('/ai/chat', { message })

# 드로우 릴레이

## 소스 구성 (Client 프로젝트 +Server 프로젝트)

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1gg90qrfm0xj30my0e4jsh.jpg" alt="image-20200629125344647" width="33%" /> 
<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1gg90q8rakdj30li0e4jsg.jpg" alt="image-20200629125314211" width="33%" /> 

### Client 프로젝트

- javafx view 파일 
- Client socket 연결
- Server에게 받은 데이터 제어 

### Server 프로젝트 

- tcp/ip 소켓 연결 

- Client의 작업 요청 실행 

- Thread Pool로 Thread 생성 및 관리

- DB연결 및 제어



## 프로젝트 설명

### 기획 의도

- 코로나때문에 타인과의 소통이 단절되어 답답한 사람들에게 공통된 키워드를 주제로 여러명이 그림을 그려서 그 외의 사람(그림을 그리지 않는 사람)이 그림을 보고 무엇인지 맞추는 게임을 통해 소통의 즐거움과 친밀감을 느낄 수 있는 만남의 장을 구현 

### 게임 설명 

![image-20201212155325523](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2swpwg9j321e0u0hdt.jpg)

![image-20201212155414212](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2tqssv4j31x80u0wp0.jpg)



### 게임 디테일

### 대기방

1. 유저들이 대기방으로 접속

   ![image-20201212155502226](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2ukplcoj31dj0ghqe5.jpg)

2. 모든 유저가 준비 버튼을 클릭해야 게임 플레이

   ![image-20201212155913217](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2yxo5qyj31xc0o21eg.jpg)

### 게임방

- 그림을 그리는 플레이어

  - 랜덤으로 순서가 정해짐

  - 해당 그림을 그리는 플레이어만 캔버스가 활성화 
  - 그려지는 그림은 (거의) 동시에 모든 유저에게 브로드캐스팅

![image-20201212155622701](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2vyx75mj31z60oatom.jpg)



- 맞추는 플레이어
  - 주제를 볼 수없음 (??? 표시로 가려짐)
  - 정답을 맞추면 바로 다음 차례로 넘어감

![image-20201212155630962](https://tva1.sinaimg.cn/large/0081Kckwgy1gll2w3t0d0j31z60oa4de.jpg)


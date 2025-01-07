# 사용 기술 스택
- spring boot 3.4.4
- spring clound stream 2023.0.4
- kafka
- redis
# 모듈 설명 
## extract-domain
function 추상화 및 input, output 데이터가 있는 곳
## extract-function
하위 모듈에 집합이고 이미지로 배포 할 수 있도록 구성 
### extract-account-function
계정 정보가져와서 다른 정보와 결합하기 위한 모듈
### extract-hadoop-function
하둡 정보를 추출하기 위한 모듈 (실제 하둡과 연결은 없음)
### extract-paymnent-function
결제 정보를 추출하기 위한 모듈
### extract-point-function
포인트 정보를 추출하기 위한 모듈
### redis-save-function
추출한 정보를 redis에 보관하기 위한 모듈

## infra
### redis
redis를 별도 모듈 분리함
### test-redis
단위 테스트시 redis testContainer로 사용하기 위함

# 흐름 
extractPaymentFunction, extractPointFunction, extractHadoopFunction -> accountResultConvertFunction -> redisSaveConsmer 순으로 데이터가 처리
extract-function 아래 test에서 통합 테스트가 진행됨 

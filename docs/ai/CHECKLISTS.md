# CHECKLISTS

## 공통 작업 루프
1) 조사(읽기): list_directory_tree, find_files_by_name_keyword/find_symbol, get_file_problems
2) 계획: 목표/단계/리스크/테스트/롤백
3) 승인받기
4) 실행: apply_refactor/replace_in_file/reformat_file → run_gradle or execute_run_configuration
5) 검증/리포트: diff 요약 + 리포트 경로

## 테스트 우선 루프
- 실패 테스트 추가 → 최소 수정 → 회귀 케이스 1개

## 빠른 명령
- 모듈 트리: list_directory_tree("app", depth=2)
- 심볼 찾기: find_symbol("VoiceTypeIndex") → 선언/참조 Top10
- 포맷: reformat_file("<경로>") → diff 요약
- 런/테스트: get_run_configurations → ":app testDebugUnitTest" 실행
- Gradle: run_gradle(":app:assembleGoogleDistributionRelease")
- 리팩터: rename_refactoring(symbol="OldName", newName="NewName") (승인 후)

package com.cj.smartworker.mongo.heart_rate.repository

object Query {
    private const val B = "$"
    private const val THIRTY_MINUTES_IN_MILLS = 1000 * 60 * 30
    const val MATCH_OPERATION: String = """
      {
        "${B}match": {
          "_id.memberId": ?0,
          "_id.timestamp": {
            "${B}gte": ?1,
            "${B}lt": ?2
          }
        }
      }
"""

    const val ADD_FIELDS_OPERATION: String = """
      {
        "${B}addFields": {
          "interval": {
            "${B}floor": {
              "${B}divide": [
                {
                  "${B}subtract": [
                    "${B}_id.timestamp",
                    ?1
                  ]
                },
                $THIRTY_MINUTES_IN_MILLS
              ]
            }
          }
        }
      }
    """

    const val GROUP_OPERATION = """
      {
        "${B}group": {
          "_id": "${B}interval",
          "averageHeartRate": {
            "${B}avg": "${B}heartRate"
          },
          "maxHeartRate": {
            "${B}max": "${B}heartRate"
          },
          "minHeartRate": {
            "${B}min": "${B}heartRate"
          }
        }
      }
    """

    const val ADD_FIELDS_DATE_OPERATION = """
      {
        "${B}addFields": {
          "dateTime": {
            "${B}dateToString": {
              "format": "%Y-%m-%dT%H:%M:%S.%LZ",
              "date": {
                "${B}add": [
                  ?1,
                  { "${B}multiply": ["${B}_id", ${THIRTY_MINUTES_IN_MILLS}] }
                ]
              }
            }
          }
        }
      }
    """

    const val SORT_OPERATION = """
          {
            "${B}sort": {
            "_id": 1
             }
          }
    """
}

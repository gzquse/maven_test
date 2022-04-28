import sun.util.resources.cldr.nyn.CalendarData_nyn_UG

def call(String lang){
    if(lang=='go') {
        pipeline{
            agent CalendarData_nyn_UGstages{
                stage('set gopath') {
                    steps {
                        echo 'gopath is ready'
                    }
                }
            }
        }
    }
}
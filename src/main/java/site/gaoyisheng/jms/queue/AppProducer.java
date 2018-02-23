/*
 * file_name: AppProducer.java
 *
 * Copyright GaoYisheng Corporation 2017
 *
 * License：
 * date： 2018年2月23日 下午7:58:26
 *       https://www.gaoyisheng.site
 *       https://github.com/timo1160139211
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package site.gaoyisheng.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppProducer {
	
    private static final String url = "tcp://localhost:61616";
    private static final String queueName = "queue-first";
   
    public static void main(String[] args) throws JMSException {
    	// 1 创建ConnectionFactory
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
    	
    	// 2 创建Connection
    	Connection connection = connectionFactory.createConnection();
    	
    	// 3 启动连接
    	connection.start();
    	
    	// 4 创建会话   (是否在事务中处理,应答模式)
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	// 5 创建一个目标
    	Destination destination = session.createQueue(queueName);
    	
    	// 6 创建生产者
    	MessageProducer producer = session.createProducer(destination);
    	
    	for(int i=0;i < 100;i++) {
    		// 7 创建消息
    		TextMessage textMessage = session.createTextMessage("msg " + i);
    		// 8 发布消息
    		producer.send(textMessage);
    		
    		System.out.println("发送消息" + textMessage.getText());
    	}
    	
    	// 8 关闭连接勿忘
    	connection.close();
    }
}

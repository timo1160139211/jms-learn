/*
 * file_name: AppConsumer.java
 *
 * Copyright GaoYisheng Corporation 2017
 *
 * License：
 * date： 2018年2月24日 上午11:13:22
 *       https://www.gaoyisheng.site
 *       https://github.com/timo1160139211
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package site.gaoyisheng.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppConsumer {

	private static final String url = "tcp://localhost:61616";
	private static final String topicName = "topic-first";

	public static void main(String[] args) throws JMSException {
		// 1 创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		// 2 创建Connection
		Connection connection = connectionFactory.createConnection();

		// 3 启动连接
		connection.start();

		// 4 创建会话 (是否在事务中处理,应答模式)
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 5 创建一个目标
		Destination destination = session.createTopic(topicName);

		// 6 创建消费者
		MessageConsumer consumer = session.createConsumer(destination);

		// 7 创建一个监听器
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接收消息" + textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// 8 关闭连接勿忘
		// connection.close();
	}
}

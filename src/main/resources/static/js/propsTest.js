class Codelab extends React.Component{
	render(){
		return(
			<div>
				<h1>Hello{this.props.name}</h1>
				<h2>{this.props.number}</h2>
				<div>{this.props.children}</div>
			</div>
		);
	}
}

//필수는 아니지만 제대로 사용, 유지보수를 위해서 만들어줌
Codelab.propTypes = {
	name:React.PropTypes.string,
	number:React.PropTypes.number.isRequired//isRequired 필수항목
}

Codelab.defaultProps = {
	name:'Unknown'
}

class App extends React.Component{
	render(){
		return(
			<Codelab number={this.props.number}>이 사이가 children</Codelab>
		);
	}
}

ReactDOM.render(<App number="777"></App>, document.getElementById('root'));
class ContactInfo extends React.Component{
	render(){
		return(
			<div>
				{this.props.contact.name} {this.props.contact.phone}
			</div>
		)
	}
}

class Contact extends React.Component{
	
	constructor(props){
		super(props);
		this.state = {
			contactData:[
				{name:'A', phone:'010-0000-0001'},
				{name:'B', phone:'010-0000-0002'},
				{name:'C', phone:'010-0000-0003'},
				{name:'D', phone:'010-0000-0004'}
			]
		}
	}
	
	render(){
		
		const mapToComponent = (data) =>{
			return data.map((contact, i) =>{
				return (<ContactInfo contact ={contact} key={i}/>);
			});
		};
		
		return(
			<div>
				{mapToComponent(this.state.contactData)}
			</div>
		);
	}
}


class App extends React.Component{
	render(){
		return(
			<Contact></Contact>
		);
	}
}

ReactDOM.render(<App></App>, document.getElementById('root'));
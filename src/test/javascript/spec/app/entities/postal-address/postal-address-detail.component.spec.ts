import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PostalAddressDetailComponent } from 'app/entities/postal-address/postal-address-detail.component';
import { PostalAddress } from 'app/shared/model/postal-address.model';

describe('Component Tests', () => {
  describe('PostalAddress Management Detail Component', () => {
    let comp: PostalAddressDetailComponent;
    let fixture: ComponentFixture<PostalAddressDetailComponent>;
    const route = ({ data: of({ postalAddress: new PostalAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PostalAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PostalAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PostalAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load postalAddress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.postalAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

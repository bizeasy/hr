import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationTypeDetailComponent } from 'app/entities/party-classification-type/party-classification-type-detail.component';
import { PartyClassificationType } from 'app/shared/model/party-classification-type.model';

describe('Component Tests', () => {
  describe('PartyClassificationType Management Detail Component', () => {
    let comp: PartyClassificationTypeDetailComponent;
    let fixture: ComponentFixture<PartyClassificationTypeDetailComponent>;
    const route = ({ data: of({ partyClassificationType: new PartyClassificationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyClassificationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyClassificationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyClassificationType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyClassificationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
